import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import KurumTemelUpdatePage from './kurum-temel-update.page-object';

const expect = chai.expect;
export class KurumTemelDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('servisTakipApp.kurumTemel.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-kurumTemel'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class KurumTemelComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('kurum-temel-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('kurum-temel');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateKurumTemel() {
    await this.createButton.click();
    return new KurumTemelUpdatePage();
  }

  async deleteKurumTemel() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const kurumTemelDeleteDialog = new KurumTemelDeleteDialog();
    await waitUntilDisplayed(kurumTemelDeleteDialog.deleteModal);
    expect(await kurumTemelDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/servisTakipApp.kurumTemel.delete.question/);
    await kurumTemelDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(kurumTemelDeleteDialog.deleteModal);

    expect(await isVisible(kurumTemelDeleteDialog.deleteModal)).to.be.false;
  }
}
