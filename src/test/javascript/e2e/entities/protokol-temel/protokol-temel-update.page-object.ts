import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ProtokolTemelUpdatePage {
  pageTitle: ElementFinder = element(by.id('servisTakipApp.protokolTemel.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  sureDurumSelect: ElementFinder = element(by.css('select#protokol-temel-sureDurum'));
  sureBitisTarihiInput: ElementFinder = element(by.css('input#protokol-temel-sureBitisTarihi'));
  protokolNuInput: ElementFinder = element(by.css('input#protokol-temel-protokolNu'));
  olurYaziNuInput: ElementFinder = element(by.css('input#protokol-temel-olurYaziNu'));
  protokolImzaTarihiInput: ElementFinder = element(by.css('input#protokol-temel-protokolImzaTarihi'));
  protokolAciklamasiInput: ElementFinder = element(by.css('input#protokol-temel-protokolAciklamasi'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setSureDurumSelect(sureDurum) {
    await this.sureDurumSelect.sendKeys(sureDurum);
  }

  async getSureDurumSelect() {
    return this.sureDurumSelect.element(by.css('option:checked')).getText();
  }

  async sureDurumSelectLastOption() {
    await this.sureDurumSelect.all(by.tagName('option')).last().click();
  }
  async setSureBitisTarihiInput(sureBitisTarihi) {
    await this.sureBitisTarihiInput.sendKeys(sureBitisTarihi);
  }

  async getSureBitisTarihiInput() {
    return this.sureBitisTarihiInput.getAttribute('value');
  }

  async setProtokolNuInput(protokolNu) {
    await this.protokolNuInput.sendKeys(protokolNu);
  }

  async getProtokolNuInput() {
    return this.protokolNuInput.getAttribute('value');
  }

  async setOlurYaziNuInput(olurYaziNu) {
    await this.olurYaziNuInput.sendKeys(olurYaziNu);
  }

  async getOlurYaziNuInput() {
    return this.olurYaziNuInput.getAttribute('value');
  }

  async setProtokolImzaTarihiInput(protokolImzaTarihi) {
    await this.protokolImzaTarihiInput.sendKeys(protokolImzaTarihi);
  }

  async getProtokolImzaTarihiInput() {
    return this.protokolImzaTarihiInput.getAttribute('value');
  }

  async setProtokolAciklamasiInput(protokolAciklamasi) {
    await this.protokolAciklamasiInput.sendKeys(protokolAciklamasi);
  }

  async getProtokolAciklamasiInput() {
    return this.protokolAciklamasiInput.getAttribute('value');
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.sureDurumSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setSureBitisTarihiInput('01-01-2001');
    await waitUntilDisplayed(this.saveButton);
    await this.setProtokolNuInput('protokolNu');
    await waitUntilDisplayed(this.saveButton);
    await this.setOlurYaziNuInput('olurYaziNu');
    await waitUntilDisplayed(this.saveButton);
    await this.setProtokolImzaTarihiInput('01-01-2001');
    await waitUntilDisplayed(this.saveButton);
    await this.setProtokolAciklamasiInput('protokolAciklamasi');
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
