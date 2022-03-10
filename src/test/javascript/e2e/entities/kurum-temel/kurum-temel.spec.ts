import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import KurumTemelComponentsPage from './kurum-temel.page-object';
import KurumTemelUpdatePage from './kurum-temel-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('KurumTemel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let kurumTemelComponentsPage: KurumTemelComponentsPage;
  let kurumTemelUpdatePage: KurumTemelUpdatePage;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();
    await signInPage.username.sendKeys(username);
    await signInPage.password.sendKeys(password);
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    kurumTemelComponentsPage = new KurumTemelComponentsPage();
    kurumTemelComponentsPage = await kurumTemelComponentsPage.goToPage(navBarPage);
  });

  it('should load KurumTemels', async () => {
    expect(await kurumTemelComponentsPage.title.getText()).to.match(/Kurum Temels/);
    expect(await kurumTemelComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete KurumTemels', async () => {
    const beforeRecordsCount = (await isVisible(kurumTemelComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(kurumTemelComponentsPage.table);
    kurumTemelUpdatePage = await kurumTemelComponentsPage.goToCreateKurumTemel();
    await kurumTemelUpdatePage.enterData();
    expect(await isVisible(kurumTemelUpdatePage.saveButton)).to.be.false;

    expect(await kurumTemelComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(kurumTemelComponentsPage.table);
    await waitUntilCount(kurumTemelComponentsPage.records, beforeRecordsCount + 1);
    expect(await kurumTemelComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await kurumTemelComponentsPage.deleteKurumTemel();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(kurumTemelComponentsPage.records, beforeRecordsCount);
      expect(await kurumTemelComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(kurumTemelComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
